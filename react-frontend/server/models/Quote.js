const { Schema, model } = require("mongoose");

const quoteSchema = new Schema(
  {
    symbol: String,
    open: Number,
    high: Number,
    low: Number,
    price: Number,
    volume: Number,
    latestTradingDay: Date,
    previousClose: Number,
    change: Number,
    changePercent: String
  });

const Quote = model("Quote", quoteSchema);

module.exports = Quote;
