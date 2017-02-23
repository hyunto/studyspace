#!/usr/bin/python
# coding=utf-8

import argparse
import subprocess
import sys, time, re
import threading

class Protocol2Command:
    def main(self):
        """This is the main function to run this program.
        usage: convert-aof.py [-h] -i inputfile -o outputfile

        optional arguments:
          -h, --help            show this help message and exit
          -i INPUTFILE, --inputfile INPUTFILE
          -o OUTPUTFILE, --outputfile OUTPUTFILE
          -v, --version         show program's version number and exit
        """
        parser = argparse.ArgumentParser(prog="convert-aof.py",
                                         usage="convert-aof.py [-h] -i inputfile -o outputfile",
                                         description="This program will convert the AOF" \
                                                     + "(Append Only File) file format from pipeline to command.",
                                         epilog="Made by Hyunsoo Jung (eqelizer@naver.com)")
        parser.add_argument("-i", "--inputfile", action="store", required=True, help="")
        parser.add_argument("-o", "--outputfile", action="store", required=True, help="")
        parser.add_argument("-v", "--version", action="version", version="protocol2command.py v1.0")
        args = parser.parse_args()
        inputfile = args.inputfile
        outputfile = args.outputfile

        file, count = self.open_file(inputfile)
        time.sleep(0.5)
        data = self.refine_data(file, count)
        time.sleep(0.5)
        result = self.convert_format(data)
        time.sleep(0.5)
        self.export_file(outputfile, result)

    def open_file(self, filename):
        """ This method will open the AOF file.
        :param filename: AOF file's name
        :return: iterator, int
        """
        print("#1. Open the AOF file")
        starttime = time.time()
        p = ProgressBarLoading()
        p.kill = False
        p.stop = False
        p.start()
        try:
            file = open(filename)
            count = int(subprocess.Popen(["wc", "-l", filename], stdout=subprocess.PIPE).communicate()[0].rstrip("\n").split()[0])
            file_generator = (t.strip() for t in file)
            p.stop = True
        except KeyboardInterrupt or EOFError:
            p.kill = True
            p.stop = True
            sys.exit(1)
        self.print_finish(starttime)
        return file_generator, count

    def refine_data(self, file, count):
        """This method will remove some lines that display the number of character.
        :param file: the list of data that is split by line.
        :param count: the total number of aof file's lines
        :return: list
        """
        print("#2. Refining the AOF file")
        starttime = time.time()
        data, idx = [], 0
        try:
            for idx in xrange(0, count+1, 1):
                var = file.next()
                if re.match('\A[$]\d', var) == None :
                    data.append(var)
                if idx != 0:
                    self.print_progressbar(idx, count)
        except StopIteration:
            pass
        except KeyboardInterrupt:
            print("\nAbort!!!")
            sys.exit(1)
        self.print_finish(starttime)
        return data

    def convert_format(self, data):
        """ This method will convert the AOF format from pipeline to command.
        :param data: the data that is removed some lines that display the number of character.
        :return:String
        """
        print("#3. Converting the AOF file format from pipeline to command")
        starttime = time.time()
        result, idx, count = "", 0, data.__len__()
        try:
            while idx < count:
                if re.match('\A[*]\d', data[idx]) != None :
                    num = str(data[idx]).lstrip('*')
                    for i in range(1, int(num) + 1, 1) :
                        result += data[int(idx + i)] + " "
                    result += "\n"
                    idx += int(num)
                idx += 1
                self.print_progressbar(idx, count)
        except KeyboardInterrupt:
            print("\nAbort!!!")
            sys.exit(1)
        self.print_finish(starttime)
        return result

    def export_file(self, filename, result):
        """ This method will make the result file.
        :param filename:
        :param result:
        """
        print("#4. Print result to a new file")
        starttime = time.time()
        p = ProgressBarLoading()
        p.kill = False
        p.stop = False
        p.start()
        try:
            with open(filename, 'wb') as file:
                file.write(result)
            p.stop = True
        except KeyboardInterrupt or EOFError:
            p.kill = True
            p.stop = True
            sys.exit(1)
        self.print_finish(starttime)

    def print_progressbar(self, idx, count):
        """This will print the progress bar during converting data.

        Example >
            Progress : [############################################......] 87%

        :param idx:
        :param count:
        :return:None
        """
        bar_length = 50
        percent = float(idx) / count
        hashes = '#' * int(round(percent * bar_length))
        spaces = '.' * (bar_length - len(hashes))
        sys.stdout.write("\rProgress : [{0}] {1}%".format(hashes + spaces, int(round(percent * 100))))
        sys.stdout.flush()

    def print_finish(self, starttime):
        """ This method will print the finish message.
        :param starttime:
        """
        print("\nDone!!! (" + str(round(time.time() - starttime, 2)) + "s)\n")

class ProgressBarLoading(threading.Thread):
    stop = None
    kill = None
    def run(self):
        """ This method will print the loading progress durint writing and reading AOF file.

            Example>
                Loading.... /
        """
        print 'Loading....  ',
        sys.stdout.flush()
        i = 0
        while self.stop != True:
            if (i%4) == 0 : sys.stdout.write('\b/')
            elif (i%4) == 1 : sys.stdout.write('\b-')
            elif (i%4) == 2 : sys.stdout.write('\b\\')
            elif (i%4) == 3 : sys.stdout.write('\b|')
            sys.stdout.flush()
            time.sleep(0.2)
            i+=1
        if self.kill == True:
            print('\nAbort!!!')

if __name__ == '__main__':
    Protocol2Command().main()