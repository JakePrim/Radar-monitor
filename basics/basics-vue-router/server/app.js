const Koa = require('koa');
const KoaRouter = require('koa-router');
const KoaStaticCache = require('koa-static-cache');
const jwt = require("jsonwebtoken");
const koaBody  = require('koa-body');
const fs = require('fs');
const path = require('path');
let items = require('./data/items.json');
let itemDetails = require('./data/itemDetails.json');
let users = require('./data/users.json');
let admins = require('./data/admins.json');
let carts = require('./data/carts.json');


const app = new Koa();
const router = new KoaRouter();

app.use( KoaStaticCache({
    prefix: '/public',
    dir: './public',
    gzip: true,
    dynamic: true
}) );

// 获取用户信息
app.use(async (ctx, next) => {
    let token = ctx.headers['authorization'] || '';
    if (token) {
        ctx.userInfo = jwt.verify(token, 'kaikeba');
    }
    await next();
});

app.use( koaBody({
    multipart: true,
    formidable: {
        uploadDir: path.resolve(__dirname, './public/images'),
        keepExtensions: true
    }
}) );

router.get('/', async ctx => {
    ctx.body = 'api';
});

router.get('/items', async ctx => {
    let {page, prepage} = ctx.request.query;
    page = Number(page) || 1;
    prepage = Number(prepage) || 10;

    items = sort(items);
    
    let start = (page - 1) * prepage;
    let end = start + prepage;
    ctx.body = {
        page,
        prepage,
        total: items.length,
        items: items.slice(start, end).map( item => {
            return {
                ...item,
                cover: 'http://localhost:7777/public/images/' + item.cover
            }
        } )
    };
});

router.get('/item/:id', async ctx => {
    
    return new Promise((resolve) => {
        setTimeout(() => {

            let id = ctx.params.id;

            let item = items.find( item => item.id == id );

            if (!item) {
                ctx.status = 404;
                ctx.body = '商品不存在';
            } else {
                item = {...item};

                item.cover = 'http://localhost:7777/public/images/' + item.cover;
                item.detail = itemDetails.find(detail => detail.itemId == id);
                ctx.body = item;
            }

            resolve();
        }, 1000)
    })
});

router.post('/register', async ctx => {
    let {name, password, repassword} = ctx.request.body;

    if (!name || !password || name.trim() === '' || password.trim() === '') {
        ctx.status = 422;
        return ctx.body = '用户名和密码不能为空';
    }

    if (password !== repassword) {
        ctx.status = 422;
        return ctx.body = '两次输入密码不一致';
    }

    let user = users.find( user => user.name == name );
    if (user) {
        ctx.status = 409;
        return ctx.body = '用户名已经被注册了';
    }

    let id = 1;
    users = sort(users);
    if (users.length) {
        id = users[0].id + 1;
    }

    let newUser = {
        id,
        name,
        password
    }

    users.unshift(newUser);

    fs.writeFileSync('./data/users.json', JSON.stringify(users));
    
    ctx.body = {
        id,
        name
    };
});

router.post('/login', async ctx => {
    console.log(ctx.request.body);
    let {name, password} = ctx.request.body;

    if (!name || !password || name.trim() === '' || password.trim() === '') {
        ctx.status = 422;
        return ctx.body = '用户名和密码不能为空';
    }

    let user = users.find( user => user.name == name );
    if (!user) {
        ctx.status = 404;
        return ctx.body = '用户不存在';
    }
    if (user.password != password) {
        ctx.status = 403;
        return ctx.body = '登陆失败';
    }

    let userInfo = {
        id: user.id,
        name: user.name
    };
    let token = jwt.sign( userInfo, 'kaikeba' );
    ctx.set('authorization', token);

    ctx.body = userInfo;
});


router.get('/carts', async ctx => {
    if (!ctx.userInfo || !ctx.userInfo.id) {
        ctx.status = 403;
        return ctx.body = '无权操作';
    }
    carts = sort(carts);
    ctx.body = carts.filter(c => c.userId == ctx.userInfo.id).map( c => {
        return {
            ...c,
            cover: 'http://localhost:7777/public/images/' + c.cover
        }
    } );
});

router.post('/carts', async ctx => {
    if (!ctx.userInfo || !ctx.userInfo.id) {
        ctx.status = 403;
        return ctx.body = '无权操作';
    }

    let {itemId, quantity} = ctx.request.body;
    if (!itemId || !quantity) {
        ctx.status = 422;
        return ctx.body = '商品id和数量不能为空';
    }

    carts = sort(carts);
    let cartItem = carts.find( c => c.itemId == itemId && c.userId == ctx.userInfo.id );

    if (!cartItem) {
        let id = 1;
        if (carts.length) {
            id = carts[0].id + 1;
        }
        cartItem = items.find(item => item.id == itemId);
        cartItem.itemId = cartItem.id;
        cartItem.id = id;
        cartItem.userId = ctx.userInfo.id;
        carts.unshift(cartItem);
    }
    cartItem.quantity = quantity;

    fs.writeFileSync('./data/carts.json', JSON.stringify(carts));

    ctx.body = cartItem;
});

router.delete('/cart/:id(\\d+)', async ctx => {
    if (!ctx.userInfo || !ctx.userInfo.id) {
        ctx.status = 403;
        return ctx.body = '无权操作';
    }

    let {id} = ctx.params;

    carts = sort(carts);
    carts = carts.filter(c => !(c.id == id && c.userId == ctx.userInfo.id));

    fs.writeFileSync('./data/carts.json', JSON.stringify(carts));

    ctx.body = '删除成功';
});


/////// 管理员
router.post('/admin/item', async ctx => {

    if (!ctx.userInfo || !ctx.userInfo.isAdmin) {
        ctx.status = 403;
        return ctx.body = '无权操作';
    }

    let {classification, name, price} = ctx.request.body;

    if (!classification || !name || !price || !ctx.request.files || !ctx.request.files.cover) {
        ctx.status = 422;
        return ctx.body = '缺少参数';
    }

    let file = ctx.request.files.cover;

    let cover = file.path.split('/').pop();

    let id = 1;
    items = sort(items);
    if (items.length) {
        id = items[0].id + 1;
    }

    let newItem = {
        id,
        name,
        classification,
        price,
        cover
    }

    items.unshift(newItem);

    fs.writeFileSync('./data/items.json', JSON.stringify(items));
    
    ctx.body = newItem;

});
router.delete('/admin/item/:id(\\d+)', async ctx => {
    if (!ctx.userInfo || !ctx.userInfo.isAdmin) {
        ctx.status = 403;
        return ctx.body = '无权操作';
    }

    let {id} = ctx.params;

    items = sort(items);
    items = items.filter(item => item.id != id);

    fs.writeFileSync('./data/items.json', JSON.stringify(items));

    ctx.body = '删除成功';
});
router.post('/admin/login', async ctx => {
    let {name, password} = ctx.request.body;

    let admin = admins.find( admin => admin.name == name );
    if (!admin || admin.password != password) {
        ctx.status = 403;
        return ctx.body = '登陆失败';
    }

    let adminInfo = {
        id: admin.id,
        name: admin.name,
        isAdmin: true
    };
    let token = jwt.sign( adminInfo, 'kaikeba' );
    ctx.set('authorization', token);

    ctx.body = adminInfo;
});


app.use(router.routes());

app.listen(7777);

function sort(data, order = 'desc') {
    data = [...data];
    data.sort( (a, b) => {
        return order === 'desc' ? b.id - a.id : a.id - b.id;
    } );
    return data;
}