import axios from 'axios'

const api = axios.create({
    baseURL: 'http://localhost:8080',
})

export const BACKEND_URL = process.env.REACT_APP_BACKEND_URL || "http://localhost:8080";
export const tradersUrl = BACKEND_URL + "traders/traders";
export const createTraderUrl = BACKEND_URL + "/trader";
export const deleteTraderUrl = BACKEND_URL + "/trader/:id";
export const dailyListQuotesUrl = BACKEND_URL + "/quote/dailyList";
export const traderAccountUrl = BACKEND_URL + "/trader/:id";
export const depositFundsUrl = BACKEND_URL + "/trader/:id";
export const withdrawFundsUrl = BACKEND_URL + "/trader/:id";

export const createTrader = payload => api.post('/trader', payload);