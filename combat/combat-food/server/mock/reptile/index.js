const puppeteer = require('puppeteer');
const pathToExtension = require('path').join(__dirname, 'chrome-mac/Chromium.app/Contents/MacOS/Chromium');
const fs =require('fs');
const path = require('path');
const meunsPath = path.join(__dirname, './menus.json');

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
    executablePath: pathToExtension,
    timeout: 3000, // 默认超时为30秒，设置为0则表示不设置超时
  });

  
  const i = 3;
  const url = `https://www.meishij.net/china-food/xiaochi/?&page=${i}`;
  const datas = await getData(browser, url);

  // const name = '家常菜谱';
  // const name = '中华菜系';
  const name = '各地小吃';

  saveMeuns({
    name,
    list: datas,
    page: 3
  })

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
  const page = await browser.newPage();
  await page.goto(url);

  const datas = await page.evaluate(() => {
    const list = $('#listtyle1_list .listtyle1');
    const data = [];
    list.each((index, item) => {
      data.push({
        link: $(item).find('a').attr('href'),
        imgSrc: $(item).find('img').attr('src')
      })
    })
    return data;
  });
  await page.close();
  return datas;
}