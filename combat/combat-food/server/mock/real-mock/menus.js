
// 给user插入菜单

const menusDetails = require('../reptile/menus_detail.json');
const users = require('../data/user.js');
const {chunk} = require('lodash');

const axios = require('axios');
const loginUrl = `http://localhost:7001/user/login`
const publishUrl = `http://localhost:7001/menu/publish`;

function randomNum(n){
  return Math.ceil(Math.random()*(n));
}

function createRandomId(n=4){
  return randomNum(n) + '-' + randomNum(n);
}

function pcreateRandomId(k,n=4){
  return k + '-' + randomNum(n);
}

menusDetails.forEach((item) => {
  item.list.forEach((option) => {
    option.product_story = '111';
    option.property =  {
      craft: pcreateRandomId(1),  // 工艺 enum: [1,2,3,4],
      flavor: pcreateRandomId(2),  // 口味  enum: [1,2,3,4],
      hard: pcreateRandomId(3),   // 难度 enum: [1,2,3,4],
      people: pcreateRandomId(4)  // pepole 人数: [1,2,3,4],
    }  // 属性
    option.classify = createRandomId(3) // 菜谱分类
  })
  item.list2 = chunk(item.list, 5);
})

users.forEach((user, index) => {
  axios.post(loginUrl, {
    name: user.name,
    password: user.password
  }).then((d) => {

    menusDetails.forEach(item => {
      item.list2[index].forEach((option) => {
        if(option.title === '') return;
        option.userId = d.data.data._id;
        option.name = d.data.data.name;
        option.skill = `这是统一的模拟的数据，来模拟烹饪技巧。在发布的新菜谱中，可以自己写烹饪技巧哦！`
        axios.post(publishUrl, option, {
          headers: {
            'authorization': d.data.data.token,
          },
        }).then(() => {
          console.log('插入成功')
        }).catch(() => {
          console.log('有问题', option);
        })
      })
    })

    
  })
  
})




const backData = {
  "title": "温车九位步切。",
  "product_pic_url": "https://s1.st.meishij.net/r/208/102/1025708/s1025708_156144940220181.jpg",
  "product_story": "价声表她里务为解身在便感加际元个认原马厂。",
  "property": {
    "craft": "1-1",
    "flavor": "2-1",
    "hard": "2-3",
    "people": "4-2"
  },
  "raw_material": {
    "main_material": [
      {
        "name": "位出我。",
        "specs": "共下叫。"
      },
      {
        "name": "位出我。",
        "specs": "共下叫。"
      },
      {
        "name": "位出我。",
        "specs": "共下叫。"
      },
      {
        "name": "位出我。",
        "specs": "共下叫。"
      }
    ],
    "accessories_material": [
      {
        "name": "速白查。",
        "specs": "的期理。"
      },
      {
        "name": "速白查。",
        "specs": "的期理。"
      },
      {
        "name": "速白查。",
        "specs": "的期理。"
      }
    ]
  },
  "steps": [
    {
      "img_url": "https://s1.st.meishij.net/rs/208/102/1025708/n1025708_156144944939153.jpg",
      "describe": "去处选由走导联立边江开争经管元等业龙力备不复物根细起带看光极她正劳人点合等八北马矿取明计族该定老公教什能风明决常应家部放程己老看作决队到整你对真没件级真约低几动回下真真飞很南月通于能取素设五光适教委此后下边外明维派派无名八造北专身质置期受半精增需东离象听也提数决完界指程空已论总线维属年阶复史听厂样元先去样各到构证他反海想论元收究品心感商型被再为并铁红更务持构法温型很海口特几史些步类温标少受拉容之儿市必结金要实去运干积低者车包特化快极声府叫况你眼带美科位并能信布受政证包比速大文风习当济近取然界今无地写该市极团别带程置矿团七家叫约全立林其整律里就到办之量车院等家比例造影而传但决变义问生般积门照花南与各局专这种统例部行。"
    },
    {
      "img_url": "https://s1.st.meishij.net/rs/208/102/1025708/n1025708_156144944939153.jpg",
      "describe": "去处选由走导联立边江开争经管元等业龙力备不复物根细起带看光极她正劳人点合等八北马矿取明计族该定老公教什能风明决常应家部放程己老看作决队到整你对真没件级真约低几动回下真真飞很南月通于能取素设五光适教委此后下边外明维派派无名八造北专身质置期受半精增需东离象听也提数决完界指程空已论总线维属年阶复史听厂样元先去样各到构证他反海想论元收究品心感商型被再为并铁红更务持构法温型很海口特几史些步类温标少受拉容之儿市必结金要实去运干积低者车包特化快极声府叫况你眼带美科位并能信布受政证包比速大文风习当济近取然界今无地写该市极团别带程置矿团七家叫约全立林其整律里就到办之量车院等家比例造影而传但决变义问生般积门照花南与各局专这种统例部行。"
    },
    {
      "img_url": "https://s1.st.meishij.net/rs/208/102/1025708/n1025708_156144944939153.jpg",
      "describe": "去处选由走导联立边江开争经管元等业龙力备不复物根细起带看光极她正劳人点合等八北马矿取明计族该定老公教什能风明决常应家部放程己老看作决队到整你对真没件级真约低几动回下真真飞很南月通于能取素设五光适教委此后下边外明维派派无名八造北专身质置期受半精增需东离象听也提数决完界指程空已论总线维属年阶复史听厂样元先去样各到构证他反海想论元收究品心感商型被再为并铁红更务持构法温型很海口特几史些步类温标少受拉容之儿市必结金要实去运干积低者车包特化快极声府叫况你眼带美科位并能信布受政证包比速大文风习当济近取然界今无地写该市极团别带程置矿团七家叫约全立林其整律里就到办之量车院等家比例造影而传但决变义问生般积门照花南与各局专这种统例部行。"
    },
    {
      "img_url": "https://s1.st.meishij.net/rs/208/102/1025708/n1025708_156144944939153.jpg",
      "describe": "去处选由走导联立边江开争经管元等业龙力备不复物根细起带看光极她正劳人点合等八北马矿取明计族该定老公教什能风明决常应家部放程己老看作决队到整你对真没件级真约低几动回下真真飞很南月通于能取素设五光适教委此后下边外明维派派无名八造北专身质置期受半精增需东离象听也提数决完界指程空已论总线维属年阶复史听厂样元先去样各到构证他反海想论元收究品心感商型被再为并铁红更务持构法温型很海口特几史些步类温标少受拉容之儿市必结金要实去运干积低者车包特化快极声府叫况你眼带美科位并能信布受政证包比速大文风习当济近取然界今无地写该市极团别带程置矿团七家叫约全立林其整律里就到办之量车院等家比例造影而传但决变义问生般积门照花南与各局专这种统例部行。"
    },
    {
      "img_url": "https://s1.st.meishij.net/rs/208/102/1025708/n1025708_156144944939153.jpg",
      "describe": "去处选由走导联立边江开争经管元等业龙力备不复物根细起带看光极她正劳人点合等八北马矿取明计族该定老公教什能风明决常应家部放程己老看作决队到整你对真没件级真约低几动回下真真飞很南月通于能取素设五光适教委此后下边外明维派派无名八造北专身质置期受半精增需东离象听也提数决完界指程空已论总线维属年阶复史听厂样元先去样各到构证他反海想论元收究品心感商型被再为并铁红更务持构法温型很海口特几史些步类温标少受拉容之儿市必结金要实去运干积低者车包特化快极声府叫况你眼带美科位并能信布受政证包比速大文风习当济近取然界今无地写该市极团别带程置矿团七家叫约全立林其整律里就到办之量车院等家比例造影而传但决变义问生般积门照花南与各局专这种统例部行。"
    }
  ],
  "classify": "1-1",
  "skill": "军单期白例心整叫质向消六节观值清成原参就等强单今置布种她成委团号引调每变重边器放量华火向那际院社度因。",
  "userId": "5d613120ac558b4d879824b5"
}
