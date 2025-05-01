var express = require('express');
var router = express.Router();


const {
    getAllQuotesController
} = require("../controllers/quoteController");
  
  // get all quotes
  router.get("/dailyList", getAllQuotesController);
  
  module.exports = router;
  