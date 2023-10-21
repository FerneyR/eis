import React, { useState } from 'react';
import { useQuery } from 'react-query';
import './App.css';

function App() {
  const [count, setCount] = useState(0);
  const [isRefreshing, setIsRefreshing] = useState(false);

  const { data, refetch } = useQuery(["tablastatus"], async () => {
    const response = await fetch("http://localhost/statusreview/statuspage");
    const data = await response.json();
    return data;
  });


  const handleRefresh = () => {
    setIsRefreshing(true);
    refetch();
    setTimeout(() => {
      setIsRefreshing(false);
    }, 6500);
  };


  return (
      <div className="app-container">
        <header>
          <h1>Attendance App - Status Page</h1>
        </header>
        <main>
          <button className="refresh-button" onClick={handleRefresh} disabled={isRefreshing}>
            Actualizar
          </button>
          <table className="data-table">
            <thead>
            <tr>
              <th>Code</th>
              <th>Name</th>
              <th>Status</th>
            </tr>
            </thead>
            <tbody>
            {data &&
                data.map((item, index) => (
                    <tr key={index} className={item.status === 'Healthy' ? 'healthy-text' : item.status === 'Unhealthy' ? 'unhealthy-text' : ''}>
                      <td>{item.code}</td>
                      <td>{item.name}</td>
                      <td>{item.status}</td>
                    </tr>
                ))}
            </tbody>
          </table>
        </main>
        <footer>
          <p>&copy; 2023 - Juan Martinez</p>
        </footer>
      </div>
  );
}

export default App;