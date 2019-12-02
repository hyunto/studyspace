/* eslint-disable require-jsdoc */
const {series, parallel} = require('gulp');
const {src, dest} = require('gulp');
const eslint = require('gulp-eslint');
const babel = require('gulp-babel');

function esLint() {
  return src('es6/**/*.js', 'public/es6/**/*.js')
      .pipe(eslint())
      .pipe(eslint.format());
}

function nodeBabel() {
  return src('es6/**/*.js')
      .pipe(babel())
      .pipe(dest('dist'));
}

function browserBabel() {
  return src('public/es6/**/*.js')
      .pipe(babel())
      .pipe(dest('public/dist'));
}

exports.default = series(
    esLint,
    parallel(nodeBabel, browserBabel),
);
