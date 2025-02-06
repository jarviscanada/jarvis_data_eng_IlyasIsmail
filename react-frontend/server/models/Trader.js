const { Schema, model } = require("mongoose");

const traderSchema = new Schema(
  {
    firstName: String,
    lastName: String,
    dob: Date,
    country: String,
    email: String,
    amount: Number
  });

const Trader = model("Trader", traderSchema);

module.exports = Trader;
