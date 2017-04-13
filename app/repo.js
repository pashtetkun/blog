const $ = require('jquery');

function getAllCategories() {
  return new Promise((resolve, reject) =>{
    $.get("/getAllCategories").done((data) => {
      resolve(JSON.parse(data));
    }).fail((error) => {
      reject(error);
    })
  })
};

function getAllSubcategories(category) {
  return new Promise((resolve, reject) =>{
    $.get(`/getAllSubcategories/${category}`).done((data) => {
      resolve(JSON.parse(data));
    }).fail((error) => {
      reject(error);
    })
  })
};

function getAllArticles(category, subcategory) {
  return new Promise((resolve, reject) =>{
    $.get(`/getAllArticles/${category}/${subcategory}`).done((data) => {
      resolve(JSON.parse(data));
    }).fail((error) => {
      reject(error)
    })
  })
};

function getArticle(category, subcategory, article) {
  return new Promise((resolve, reject) =>{
    $.get(`/getArticle/${category}/${subcategory}/${article}`).done((data) => {
      resolve(JSON.parse(data));
    }).fail((error) => {
      reject(error)
    })
  })
};

module.exports = { getAllCategories, getAllSubcategories, getAllArticles, getArticle };
