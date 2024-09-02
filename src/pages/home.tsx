import { useEffect } from 'react';
import { Helmet } from 'react-helmet-async';
import { useNavigate } from 'react-router-dom'; // Import useNavigate for redirection

import { OverviewAnalyticsView } from 'src/sections/overview/view';

export default function Page() {
    const navigate = useNavigate(); // Hook to handle navigation

    useEffect(() => {
        const token = sessionStorage.getItem('token');
        if (!token) {
            // Redirect to the sign-in page if no token is found
            navigate('/sign-in');
        }
    }, [navigate]);

    return (
        <>
            <Helmet>
                {/* eslint-disable-next-line react/no-unescaped-entities */}
                <title>MANA'S Chemist</title>
            </Helmet>
            <OverviewAnalyticsView />
        </>
    );
}
