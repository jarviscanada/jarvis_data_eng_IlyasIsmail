var express = require('express');
var router = express.Router();

const {
    getAllTradersController,
    getTraderController,
    addTraderController,
    updateTraderController,
    deleteTraderController
  } = require("../controllers/traderController");
  
  // get all traders
  router.get("/traders", getAllTradersController);
  
  // get single trader
  router.get("/trader/:id", getTraderController);

  // add trader
  router.post("/trader", addTraderController);
  
  // update trader
  router.patch("/trader/:id", updateTraderController);

  // delete trader
  router.delete("/trader/:id", deleteTraderController);
  
  module.exports = router;
  