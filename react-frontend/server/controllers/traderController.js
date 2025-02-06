const Trader = require("../models/Trader");

// get all users controller
const getAllTradersController = async (req, res) => {
  try {
    const traders = await Trader.find().sort({ updatedAt: -1 });
    res.status(200).json(traders);
  } catch (err) {
    console.error(err);
    res.status(500).json({
      error: "Server error occurred",
    });
  }
};

// get user controller
const getTraderController = async (req, res) => {
  try {
    const trader = await Trader.findById({"_id": req.params.id});
    console.log(trader);
    res.status(200).json(trader);
  } catch (err) {
    console.error(err);
    res.status(500).json({
      error: "Server error occurred",
    });
  }
};

const addTraderController = async (req, res) => {

    let newTrader = Trader({
        _id: req.body.id,
        firstName: req.body.firstName,
        lastName: req.body.lastName,
        dob: req.body.dob,
        country: req.body.country,
        email: req.body.email,
        amount: req.body.amount
    });

    Trader.create(newTrader)
    .then((trader) => {
      console.log(trader);
    })
    .catch((err) => {
      console.log(err);
    });
};

// update user controller
const updateTraderController = async (req, res) => {
  try {
    const updatedTrader = await Trader.findByIdAndUpdate(
      {"_id": req.params.id},
      { $set: req.body },
      { new: true }
    );
    res.status(200).json(updatedTrader);
  } catch (err) {
    console.error(err);
    res.status(500).json({
      error: "Server error occurred",
    });
  }
};

const deleteTraderController = async (req, res) => {
    try {
        const deletedUser = await Trader.deleteOne({"_id": req.params.id});
        console.log(deletedUser);
    } catch (err) {
        console.error(err);
        res.status(500).json({ 
            error: "Server error occurred",
          });
    }
};

module.exports = {
  getAllTradersController,
  getTraderController,
  addTraderController,
  updateTraderController,
  deleteTraderController
};
