module.exports = {
  returnBody(status=200, mes="成功", data={}){
    this.status = status;
    this.body = {
      status,
      mes,
      data
    }
  },
  upload: {
    step: {
      path: '/upload/step',
      width: 550,
      height: 400,
      size: 1024 * 1024 * 10
    },
    product: {
      path: '/upload/product',
      width: 328,
      height: 440,
      size: 1024 * 1024 * 10,
      just: true
    },
    user:{
      path: '/upload/user',
      width: 210,
      height: 210,
      size: 1024 * 1024 * 2,
      just: true
    }
  }
}