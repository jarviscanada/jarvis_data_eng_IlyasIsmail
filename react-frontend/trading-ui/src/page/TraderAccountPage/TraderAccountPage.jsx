
import React, { useEffect, useState } from "react";
import "./TraderAccountPage.scss";
import {
    traderAccountUrl,
    withdrawFundsUrl,
    depositFundsUrl,
} from "../../util/constants";
import "antd/dist/antd.min.css";
import { Input, Modal, Button } from "antd";
import { useParams } from "react-router-dom";
import NavBar from "../../component/NavBar/NavBar";
import axios from "axios";

function TraderAccountPage() {

    const routeParams = useParams();

    const [state, setState] = useState({
        trader: [],
    });

    function fixDate(date, separator) {
        const dates = date.split(separator);
    
        return dates[0];
      }

    const fetchTrader = async (_id) => {
        await axios.get(`http://localhost:8080/traders/trader/${_id}`, {id: _id})
        .then(response => {
            response.data.dob = fixDate(response.data.dob, "T");

            if (response) {
                setState({
                    ...state,
                    trader: response.data,
                });
            }
        })        
    };

    useEffect(() => {
        if (routeParams && routeParams._id) {
            const _id = routeParams._id;
            setState({
                ...state,
                _id,
            });
            fetchTrader(_id);
        }
    }, []);


    const showDepositModal = () => {
        setState({
            ...state,
            isDepositModalVisible: true
        });
        console.log(state.depositFunds);
    }

    const showWithdrawModal = () => {
        setState({
            ...state,
            isWithdrawModalVisible: true
        });
    }

    const handleDepositCancel = () => {
        setState({
            ...state,
            isDepositModalVisible: false,
            depositFunds: null
        });
    }

    const handleWithdrawCancel = () => {
        setState({
            ...state,
            isWithdrawModalVisible: false,
            depositFunds: null
        });
    }

    const handleDepositOk = async () => {
        const _id = routeParams._id;
        state.trader.amount = Number(state.trader.amount) + Number(state.depositFunds);
        const response = await axios.patch(`http://localhost:8080/traders/trader/${_id}`, {id: _id, amount: state.trader.amount});
        
        if (response) {
            await fetchTrader(_id);
        }

        setState({
            ...state,
            isDepositModalVisible: false,
            depositFunds: null
        });
    }

    const handleWithdrawOk = async () => {
        const _id = routeParams._id;
        state.trader.amount = Number(state.trader.amount) - Number(state.withdrawFunds);
        const response = await axios.patch(`http://localhost:8080/traders/trader/${_id}`, {id: _id, amount: state.trader.amount});
        
        if (response) {
            await fetchTrader(_id);
        }

        setState({
            ...state,
            isDepositModalVisible: false,
            depositFunds: null
        });
    }

    const onInputChange = (field, value) => {
        setState({
            ...state,
            [field]: value
        });
    }

    return (
        <div className="trader-account-page">
            <NavBar />
            <div className="trader-account-page-content">
                <div className="title">
                    Trader Account
                </div>
                <div className="trader-cards">
                    <div className="trader-card">
                        <div className="info-row">
                            <div className="field">
                                <div className="content-heading">
                                    First Name
                                </div>
                                <div className="content">
                                    e {state.trader.firstName}
                                </div>
                            </div>
                            <div className="field">
                                <div className="content-heading">
                                    Last Name
                                </div>
                                <div className="content">
                                    {state.trader.lastName}
                                </div>
                            </div>
                        </div>
                        <div className="info-row">
                            <div className="field">
                                <div className="content-heading">
                                    Email
                                </div>
                                <div className="content">
                                    {state.trader.email}
                                </div>
                            </div>
                        </div>
                        <div className="info-row">
                            <div className="field">
                                <div className="content-heading">
                                    Date of Birth
                                </div>
                                <div className="content">
                                    {state.trader.dob}
                                </div>
                            </div>
                            <div className="field">
                            {state.trader.country}
                            </div>
                        </div>
                    </div>
                    <div className="trader-card">
                        <div className="info-row">
                            <div className="field">
                                <div className="content-heading amount">
                                    Amount
                                </div>
                                <div className="content amount">
                                    ${state.trader.amount}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="actions">
                        <Button onClick={showDepositModal}>Deposit Funds</Button>
                        <Modal title="Deposit Funds" okText="Submit" open={state.isDepositModalVisible} onOk={handleDepositOk} onCancel={handleDepositCancel}>
                            <div className="funds-form">
                                <div className="funds-field">
                                    <Input allowClear={false} placeholder="Funds" onChange={(event) => onInputChange("depositFunds", event.target.value)} />
                                </div>
                            </div>
                        </Modal>
                        <Button onClick={showWithdrawModal}>Deposit Funds</Button>
                        <Modal title="Withdraw Funds" okText="Submit" open={state.isWithdrawModalVisible} onOk={handleWithdrawOk} onCancel={handleWithdrawCancel}>
                            <div className="funds-form">
                                <div className="funds-field">
                                    <Input allowClear={false} placeholder="Funds" onChange={(event) => onInputChange("withdrawFunds", event.target.value)} />
                                </div>
                            </div>
                        </Modal>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default TraderAccountPage;