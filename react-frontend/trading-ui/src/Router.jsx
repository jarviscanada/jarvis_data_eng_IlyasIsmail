import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Dashboard from './page/Dashboard/Dashboard';
import TraderAccountPage from './page/TraderAccountPage/TraderAccountPage';
import QuotePage from './page/QuotePage/QuotePage';

export default function Router() {
        return (
            <BrowserRouter>
                <Routes>
                    <Route exact path="/" element={<Dashboard />} />
                    <Route exact path="/traders" element={<Dashboard />} />
                    <Route exact path="/trader/:_id" element={<TraderAccountPage />} />
                    <Route exact path="/quotes" element={<QuotePage />} />
               </Routes>
            </BrowserRouter>
        )
    }