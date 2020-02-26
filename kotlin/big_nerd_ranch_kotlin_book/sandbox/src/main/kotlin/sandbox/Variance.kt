package sandbox

class Barrel<out T> (item: T)

fun main(args: Array<String>) {
	var fedoraBarrel: Barrel<Fedora> = Barrel(Fedora("평범한 중절모", 15))
	var lootBarrel: Barrel<Loot> = Barrel(Coin(15))

	// in
//	fedoraBarrel = lootBarrel

	// out
	lootBarrel = fedoraBarrel	// Compile Error: Type mismatch (Required: Barrel<Loot>, Found: Barrel<Fedora>)
//	lootBarrel.item = Coin(15)	// Compile Error: Type mismatch (Required: Fedora, Found: Coin)
//
//	val myFedora: Fedora = lootBarrel.item
}