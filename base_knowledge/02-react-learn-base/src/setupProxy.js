const { createProxyMiddleware } = require("http-proxy-middleware");

/**
 * 配置多个地址
 */
module.exports = (app) => {
  app.use(
    "/api",
    createProxyMiddleware({
      target: "http://localhost:3005",
      changeOrigin: true,
    })
  );
  app.use(
    "/upload",
    createProxyMiddleware({
      target: "http://localhost:3006",
      changeOrigin: true,
    })
  );
  //其他地址类似
};
