module.exports = {
    pages: {
        index: {
            entry: 'src/main.js',
        },
    },
    lintOnSave: false, //关闭语法检查
    //开启代理服务器（方式1）
    // devServer: {
    //     proxy: 'http://localhost:8888'
    // }

    // 开启代理服务器（方式2）
    devServer: {
        proxy: {
            '/api': {
                // target: 'http://localhost:8888/',
                target: 'http://120.76.136.205:8888/',
                pathRewrite: { '/api': '' },
                ws: true, //用于支持websocket
                changeOrigin: true //用于控制请求头中的host值
            },
            // '/foo': {
            //     target: '<other_url>'
            // }
        }
    }
}
