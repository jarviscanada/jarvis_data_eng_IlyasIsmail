import React from 'react';
import { Table } from 'antd';
import 'antd/dist/antd.css'
import './QuoteList.scss'
import { useState, useEffect } from 'react'
import QuoteListData from './QuoteListData.json' 


function QuoteList(props) {

    // Initialization of columns for table
    const columns = [
        {
            title: 'Symbol',
            dataIndex: 'symbol',
            key: 'symbol',
        },
        {
            title: 'Open',
            dataIndex: 'open',
            key: 'open',
        },
        {
            title: 'High',
            dataIndex: 'high',
            key: 'high',
        },
        {
            title: 'Low',
            dataIndex: 'low',
            key: 'low',
        },
        {
            title: 'Price',
            dataIndex: 'price',
            key: 'price',
        },
        {
            title: 'Volume',
            dataIndex: 'volume',
            key: 'volume',
        },
        {
            title: 'Latest Trading Day',
            dataIndex: 'latestTradingDay',
            key: 'latestTradingDay',
        },
        {
            title: 'Previous Close',
            dataIndex: 'previousClose',
            key: 'previousClose',
        },
        {
            title: 'Change',
            dataIndex: 'change',
            key: 'change',
        },
        {
            title: 'Change Percent',
            dataIndex: 'changePercent',
            key: 'changePercent',
        },
    ];

    const [tableColumns, setTableColumns] = useState(columns)
    const [dataSource, setDataSource] = useState([])

    useEffect(() => {
        const dataSource = QuoteListData
        setDataSource(dataSource)
    })


  return (
   <Table
   dataSource={props.quotes}
   columns={tableColumns}
   pagination={false}
   />
  )
}

export default QuoteList