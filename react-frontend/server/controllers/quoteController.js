const Quote = require("../models/Quote")

const getAllQuotesController = async (req, res) => {
    try {
      const quotes = await Quote.find().sort({ updatedAt: -1 });
      res.status(200).json(quotes);
    } catch (err) {
      console.error(err);
      res.status(500).json({
        error: "Server error occurred",
      });
    }
  };

module.exports = {
  getAllQuotesController
};