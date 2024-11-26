import React from 'react';

const Home = ({ user }) => {
    return (
        <div>
            <h1>Welcome, {user ? user.firstName : 'Guest'}!</h1>
            <p>This is the home page.</p>
        </div>
    );
};

export default Home;
