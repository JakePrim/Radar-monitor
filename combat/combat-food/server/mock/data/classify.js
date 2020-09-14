const MeunClassification = [
  {
    name: '家常菜谱',
    type: 1,
    list: [
      {
        name: '家常菜',
        type: '1-1'
      },
      {
        name: '私房菜',
        type: '1-2'
      },
      {
        name: '凉菜',
        type: '1-3'
      }
    ]
  },
  {
    name: '中华菜系',
    type: 2,
    list: [
      {
        name: '川菜',
        type: '2-1'
      },
      {
        name: '湘菜',
        type: '2-2'
      },
      {
        name: '粤菜',
        type: '2-3'
      }
    ]
  },
  {
    name: '各地小吃',
    type: 3,
    list: [
      {
        name: '四川小吃',
        type: '3-1'
      },
      {
        name: '广东小吃',
        type: '3-2'
      },
      {
        name: '北京小吃',
        type: '3-3'
      },
      {
        name: '陕西小吃',
        type: '3-4'
      }
    ]
  }
]
let datas = [];
MeunClassification.forEach((item) => {
  let parent_type = item.type;
  let parent_name = item.name;
  item.list.forEach((options) => {
    datas.push({
      ...options,
      parent_type,
      parent_name
    })
  })
})

module.exports = datas;