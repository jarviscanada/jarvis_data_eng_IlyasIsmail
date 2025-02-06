import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Dashboard from './page/Dashboard/Dashboard';
import TraderAccountPage from './page/TraderAccountPage/TraderAccountPage';

export default function Router() {
        return (
            <BrowserRouter>
                <Routes>
                    <Route exact path="/" element={<Dashboard />} />
                    <Route exact path="/dashboard" element={<Dashboard />} />
                    <Route exact path="/trader/:_id" element={<TraderAccountPage />} />
               </Routes>
            </BrowserRouter>
        )
    }