import React from 'react'
import './Dashboard.scss'
import NavBar from '../../component/NavBar/NavBar'
import TraderList from '../../component/TraderList/TraderList'
import TraderListData from '../../component/TraderList/TraderListData.json'
import { Input, DatePicker, Modal, Button, Form } from 'antd'
import axios from 'axios'
import { createTraderUrl, deleteTraderUrl, tradersUrl } from '../../util/constants'
import "antd/dist/antd.min.css"
import { useEffect, useState } from 'react'

function Dashboard(props) {
		// Initializing State
    const [state, setState] = useState({
        isModalVisible: false,
        traders: []
    })

    const getTraders = () => {
        setState({
            ...state,
            traders: [...TraderListData]
        })
    }

    const showModal = () => {
        setState({
            ...state,
            isModalVisible: true
        })
    }

    const handleOk = async () => {
        // Here we would send a request to the backend to create a new trader
        // After creating a new trader, refresh traders list
        await getTraders();
        // Close the modal & unset all fields
        setState({
            ...state,
            isModalVisible: false,
            firstName: null,
            lastName: null,
            dob: null,
            country: null,
            email: null
        });
    };

    const onInputChange = (field, value) => {
        setState({
            ...state,
            [field]: value
        })
    }

    const handleCancel = () => {
        setState({
            ...state,
            isModalVisible: false,
            firstName: null,
            lastName: null,
            dob: null,
            country: null,
            email: null
        });
    }

    const onTraderDelete = async (id) => {
        console.log("Trader " + id + " is deleted.")
        // axios.delete("")
        await getTraders()
    }

  return (
    <div className="dashboard">
        <NavBar />
        <div className="dashboard-content">
        <div className="title">
                        Dashboard
                        <div className="add-trader-button">
                            <Button onClick={showModal}>Add New Trader</Button>
                            <Modal title="Add New Trader"  okText="Submit" open={state.isModalVisible} onOk={handleOk} onCancel={handleCancel}>
                                <Form
                                    layout="vertical"
                                >
                                    <div className="add-trader-form">
                                        <div className="add-trader-field">
                                            <Form.Item label="First Name">
                                                <Input allowClear={false} placeholder="John" onChange={(event) => onInputChange("firstName", event.target.value)} />
                                            </Form.Item>
                                        </div>
                                        <div className="add-trader-field">
                                            ... last name ...
                                        </div>
                                        <div className="add-trader-field">
                                            ... email ...
                                        </div>
                                        <div className="add-trader-field">
                                            ... country ...
                                        </div>
                                        <div className="add-trader-field">
                                            <Form.Item label="Date of Birth">
                                                <DatePicker style={{width:"100%"}} placeholder="" onChange={(date, dateString) => onInputChange("dob", date.format("yyyy-MM-DD"))} />
                                            </Form.Item>
                                        </div>
                                    </div>
                                </Form>
                            </Modal>
                        </div>
                    </div>
            <TraderList onTraderDeleteClick={onTraderDelete} />
        </div>
    </div>
  )
}

export default Dashboard