import React from 'react'
import './Dashboard.scss'
import NavBar from '../../component/NavBar/NavBar'
import TraderList from '../../component/TraderList/TraderList'
import TraderListData from '../../component/TraderList/TraderListData.json'
import { Input, DatePicker, Modal, Button, Form } from 'antd'
import axios from 'axios'
import { createTraderUrl, deleteTraderUrl, tradersUrl, createTrader } from '../../util/constants'
import "antd/dist/antd.min.css"
import { useEffect, useState } from 'react'

function Dashboard(props) {

    
    // Initializing State
    const [state, setState] = useState({
        isModalVisible: false,
        traders: []
    })

    const getTraders = async () => {
        const response = await axios.get("http://localhost:8080/traders/traders");

        if (response) {
            setState({
                ...state,
                traders: [...response.data] || []
            })
        }
    }

    const showModal = () => {
        setState({
            ...state,
            isModalVisible: true
        })
    }

    useEffect(() => {
        getTraders();
    }, [])

    const handleOk = async () => {
        // Here we would send a request to the backend to create a new trader
        // After creating a new trader, refresh traders list
        // Close the modal & unset all fields

        try {

            const response = await axios.post("http://localhost:8080/traders/trader", {firstName: state.firstName, lastName: state.lastName, email: state.email, country: state.country, dob: state.dob})
            .then(await getTraders());
            

            setState({
                ...state,
                isModalVisible: false,
                firstName: null,
                lastName: null,
                dob: null,
                country: null,
                email: null
            });
        } catch (e) {
            console.error(e);
        }
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

    const onTraderDelete = async (_id) => {
        console.log("Trader " + _id + " is deleted.")
        await axios.delete(`http://localhost:8080/traders/trader/${_id}`, {id: _id})
        .then(await getTraders());
    }

    return (
        <div className="dashboard">
            <NavBar />
            <div className="dashboard-content">
                <div className="title">
                    Dashboard
                    <div className="add-trader-button">
                        <Button onClick={showModal}>Add New Trader</Button>
                        <Modal title="Add New Trader" okText="Submit" open={state.isModalVisible} onOk={handleOk} onCancel={handleCancel}>
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
                                        <Form.Item label="Last Name">
                                            <Input allowClear={false} placeholder="Doe" onChange={(event) => onInputChange("lastName", event.target.value)} />
                                        </Form.Item>
                                    </div>
                                    <div className="add-trader-field">
                                        <Form.Item label="Email">
                                            <Input allowClear={false} placeholder="JohnDoe@hotmail.com" onChange={(event) => onInputChange("email", event.target.value)} />
                                        </Form.Item>
                                    </div>
                                    <div className="add-trader-field">
                                        <Form.Item label="Country">
                                            <Input allowClear={false} placeholder="" onChange={(event) => onInputChange("country", event.target.value)} />
                                        </Form.Item>
                                    </div>
                                    <div className="add-trader-field">
                                        <Form.Item label="Date of Birth">
                                            <DatePicker style={{ width: "100%" }} placeholder="" onChange={(date, dateString) => onInputChange("dob", date.format("yyyy-MM-DD"))} />
                                        </Form.Item>
                                    </div>
                                </div>
                            </Form>
                        </Modal>
                    </div>
                </div>
                <TraderList onTraderDeleteClick={ onTraderDelete } traders={ state.traders } />
            </div>
        </div>
    )
}

export default Dashboard