module.exports = {
  root: true,
  env: {
    node: true
  },
  extends: [
    'plugin:vue/essential',
    '@vue/standard'
  ],
  parserOptions: {
    parser: 'babel-eslint'
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'quotes': [0, 'single'], //引号类型 `` "" ''
    'semi': [0, 'never'], // 语句强制分号结尾
    'space-before-function-paren': 0,
    'object-curly-spacing': 0,
    'no-trailing-spaces': 0,
    'space-before-blocks': 0
  }
}
