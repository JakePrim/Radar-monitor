'use strict';

/** @type Egg.EggPlugin */
exports.mongoose = {
  enable: true,
  package: 'egg-mongoose',
};

exports.bcrypt = {
  enable: true,
  package: 'egg-bcrypt'
}

exports.jwt = {
  enable: true,
  package: 'egg-jwt',
}

exports.validate = {
  enable: true,
  package: 'egg-validate',
};
exports.validatePlus = {
  enable: true,
  package: 'egg-validate-plus',
};
