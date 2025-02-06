import React from 'react'
import './QuotePage.scss'
import axios from 'axios';
import { useState, useEffect } from 'react'
import NavBar from '../../component/NavBar/NavBar';
import QuoteList from '../../component/QuoteList/QuoteList';
import { dailyListQuotesUrl } from '../../util/constants';

function QuotePage(props) {

  const [state, setState] = useState({
    quotes: []
  })

  function fixDate(date, separator) {
    const dates = date.split(separator);

    return dates[0];
}

  const getQuotes = async () => {
    let response = await axios.get(dailyListQuotesUrl)
    .then(response => {
        response.data.forEach(quote => {
            if (quote.latestTradingDay != null || "") {
                quote.latestTradingDay = fixDate(quote.latestTradingDay, "T");
            }
        })

        if (response) {
            setState({
                ...state,
                quotes: [...response.data] || []
            });
        }
    });
  }

  useEffect(() => {
      getQuotes();
  }, [])
  
  return (
    <div className="dashboard">
        <NavBar />
        <div className="dashboard-content">
            <div className="title">
                Dashboard
            </div>
            <QuoteList quotes={ state.quotes } />
        </div>
    </div>
)
}

export default QuotePage
