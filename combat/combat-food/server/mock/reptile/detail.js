const puppeteer = require('puppeteer');
const pathToExtension = require('path').join(__dirname, 'chrome-mac/Chromium.app/Contents/MacOS/Chromium');
const fs =require('fs');
const path = require('path');

const meunsDetailPath = path.join(__dirname, './menus_detail.json');
const menus = require('./menus.json');

const sleep = (t) => {
  return new Promise((r) => {
    setTimeout(() => {
      r();
    },t)
  })
}

async function run(){
  console.log('开始')
  const browser = await puppeteer.launch({
    headless: true,
    slowMo: 250,
    executablePath: pathToExtension,
    timeout: 3000, // 默认超时为30秒，设置为0则表示不设置超时
  });
  let menus_details = [];
  for(let i = 0; i < menus.length; i++){
    const name = menus[i].name;
    const list = menus[i].list;
    const obj = {
      name,
      list:[]
    }
    menus_details.push(obj)
    for(let i = 0; i < list.length; i++){
      const datas = await getData(browser, list[i].link);
      console.log('-------- 已抓取完毕 ---------');
      obj.list.push(datas);
    }
  }

  fs.writeFileSync(meunsDetailPath, JSON.stringify(menus_details, null, 2), {encoding: 'utf-8', flag: 'w'});

  console.log('全部抓取完毕')


  await browser.close();
  console.log('已关闭')
}

run();


function saveMeuns(data){
  const meuns = require(meunsPath);
  const list = meuns.find(item => item.name === data.name);
  const d = [...meuns]
  if(list) {
    list.list = [
      ...list.list,
      ...data.list
    ]
  }else {
    d.push(data);
  }
  
  fs.writeFileSync(meunsPath, JSON.stringify(d, null, 2), {encoding: 'utf-8', flag: 'w'});
}

async function getData(browser, url){
  console.log('抓取地址：'+ url);
  console.log('打开新窗口')
  const page = await browser.newPage();
  page.on('console', (m) => {
    //console.log(m)
  })
  await page.goto(url,{waitUntil: 'networkidle2'});
  console.log('已打开新窗口打开新窗口，准备执行脚本')

  const datas = await page.evaluate(async () => {
    console.log('执行脚本 start1')
    const productImg = $('.cp_headerimg_w img');
    const tongji_title = $('#tongji_title');
    const materials_p = $('.materials p');
    let subtitle = '';
    if(materials_p.length) subtitle = materials_p.html().trim();
    const zl = $('.zl ul li'); // 主料 
    console.log('执行脚本 start2')
    let raw_material = {main_material:[], accessories_material:[]}
    zl.each((index, item) => {
      raw_material.main_material.push({
        name: $(item).find('h4 a').text().trim() || '',
        specs: $(item).find('h4 span').text().trim() || ''
      })
    })
    console.log('执行脚本 start3')
    // 辅料
    const fuliao = $('.fuliao ul li');
    fuliao.each((index, item) => {
      raw_material.accessories_material.push({
        name: $(item).find('h4 a').text().trim() || '',
        specs: $(item).find('h4 span').text().trim() || ''
      })
    })
    // 步骤
    const editnew = $('.editnew');
    let steps = [];
    if(editnew.length){
      const content = editnew.find('.content');
      content.each((index, item) => {
        const p =  $(item).find('.c p');
        steps.push({
          img_url: p.eq(1).find('img').attr('src') || '',
          describe: p.eq(0).text().trim() || ''
        })
      })
    }else {
      const edit = $('.edit p')
      edit.each((index, item) => {
        const p =  $(item).text().trim();
        if(p){
          steps.push({
            img_url: '',
            describe: p || ''
          })
        }
        
      })
    }
    console.log('执行脚本 end');
    return {
      product_pic_url: productImg.attr('src'),
      title: tongji_title.text().trim() || '',
      subtitle,
      raw_material,
      steps
    };
  });
  datas.targetUrl = url;
  console.log('关闭之前');
  await page.close();
  return datas;
}