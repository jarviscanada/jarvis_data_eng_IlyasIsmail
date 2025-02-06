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
import { useNavigate } from 'react-router-dom'

function Dashboard(props) {
    const navigate = useNavigate();

    // Initializing State
    const [state, setState] = useState({
        isModalVisible: false,
        traders: []
    })

    const [form] = Form.useForm();

    function fixDate(date, separator) {
        const dates = date.split(separator);

        return dates[0];
    }

    const getTraders = async () => {
        await axios.get(tradersUrl)
            .then(response => {
                response.data.forEach(trader => {
                    if (trader.dob != null || "") {
                        trader.dob = fixDate(trader.dob, "T");
                    }
                })

                if (response) {
                    setState({
                        ...state,
                        traders: [...response.data] || []
                    })
                }
            })


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
        try {

            const response = await axios.post(createTraderUrl, { firstName: state.firstName, lastName: state.lastName, email: state.email, country: state.country, dob: state.dob, amount: 0 })
                .then(await getTraders()
                    .then(
                        form.resetFields(),
                        state.isModalVisible = false
                    ));

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

        form.resetFields();
    }

    const onTraderDelete = async (_id) => {
        await axios.delete(`http://localhost:8080/traders/trader/${_id}`, { id: _id })
            .then(await getTraders());
    }
    
    const onTraderView = async (_id) => {
        navigate(`/trader/${_id}`);
    }

    return (
        <div className="dashboard">
            <NavBar />
            <div className="dashboard-content">
                <div className="title">
                    Dashboard
                    <div className="add-trader-button">
                        <Button onClick={showModal}>Add New Trader</Button>
                        <Modal title="Add New Trader" okText="Submit" open={state.isModalVisible}
                            footer={[
                                <div>
                                    <Button form="myForm" key="submit" htmlType="submit">
                                        Submit
                                    </Button>
                                    <Button key="back" onClick={handleCancel}>
                                        Cancel
                                    </Button>
                                </div>
                            ]}>
                            <Form
                                form={form}
                                id="myForm"
                                onFinish={handleOk}
                                layout="vertical"
                                initialValues={{ remember: true }}
                            >
                                <div className="add-trader-form">
                                    <div className="add-trader-field">
                                        <Form.Item label="First Name"
                                            name="First Name"
                                            rules={[
                                                {
                                                    required: true,
                                                    message: 'Please input your first name!',
                                                },
                                            ]}>
                                            <Input allowClear={false} placeholder="John" onChange={(event) => onInputChange("firstName", event.target.value)} />
                                        </Form.Item>
                                    </div>
                                    <div className="add-trader-field">
                                        <Form.Item label="Last Name"
                                            name="Last Name"
                                            rules={[
                                                {
                                                    required: true,
                                                    message: 'Please input your last name!',
                                                },
                                            ]}>
                                            <Input allowClear={false} placeholder="Doe" onChange={(event) => onInputChange("lastName", event.target.value)} />
                                        </Form.Item>
                                    </div>
                                    <div className="add-trader-field">
                                        <Form.Item label="Email"
                                            name="Email"
                                            rules={[
                                                {
                                                    required: true,
                                                    message: 'Please input your email!',
                                                },
                                            ]}>
                                            <Input allowClear={false} placeholder="JohnDoe@hotmail.com" onChange={(event) => onInputChange("email", event.target.value)} />
                                        </Form.Item>
                                    </div>
                                    <div className="add-trader-field">
                                        <Form.Item label="Country"
                                            name="Country"
                                            rules={[
                                                {
                                                    required: true,
                                                    message: 'Please input your country!',
                                                },
                                            ]}>
                                            <Input allowClear={false} placeholder="" onChange={(event) => onInputChange("country", event.target.value)} />
                                        </Form.Item>
                                    </div>
                                    <div className="add-trader-field">
                                        <Form.Item label="Date of Birth"
                                            name="Date of Birth"
                                            rules={[
                                                {
                                                    required: true,
                                                    message: 'Please input your date of birth!',
                                                },
                                            ]}>
                                            <DatePicker style={{ width: "100%" }} placeholder="" onChange={(date, dateString) => onInputChange("dob", date.format("yyyy-MM-DD"))} />
                                        </Form.Item>
                                    </div>
                                </div>
                            </Form>
                        </Modal>
                    </div>
                </div>
                <TraderList onTraderDeleteClick={onTraderDelete} onTraderViewClick={onTraderView} traders={state.traders} />
            </div>
        </div>
    )
}

export default Dashboard