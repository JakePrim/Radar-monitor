let property = [
  {
    name: '工艺',
    type: 1,
    title: 'craft',
    list: [
      {
        name: '炒',
        type: '1-1'
      },
      {
        name: '蒸',
        type: '1-2'
      },
      {
        name: '煮',
        type: '1-3'
      },
      {
        name: '炖',
        type: '1-4'
      }
    ]
  },
  {
    name: '口味',
    title: 'flavor',
    type: 2,
    list: [
      {
        name: '家常味',
        type: '2-1'
      },
      {
        name: '香辣味',
        type: '2-2'
      },
      {
        name: '咸鲜味',
        type: '2-3'
      },
      {
        name: '甜味',
        type: '2-4'
      }
    ]
  },
  {
    name: '难度',
    title: 'hard',
    type: 3, 
    list: [
      {
        name: '新手尝试',
        type: '3-1'
      },
      {
        name: '初级入门',
        type: '3-2'
      },
      {
        name: '初中水平',
        type: '3-3'
      },
      {
        name: '初中水平',
        type: '3-4'
      }
    ]
  },
  {
    name: '人数',
    title: 'people',
    type: 4,
    list: [
      {
        name: '1人份',
        type: '4-1'
      },
      {
        name: '2人份',
        type: '4-2'
      },
      {
        name: '3人份',
        type: '4-3'
      },
      {
        name: '4人份',
        type: '4-4'
      }
    ]
  }
]
let datas = [];
property.forEach((item) => {
  let parent_type = item.type;
  let parent_name = item.name;
  let title = item.title;
  item.list.forEach((options) => {
    datas.push({
      ...options,
      parent_type,
      parent_name,
      title
    })
  })
})

module.exports = datas;