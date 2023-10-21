import React from 'react'
import ReactDOM from 'react-dom/client'
import { QueryClientProvider, QueryClient} from 'react-query'
import App from './App'
import './index.css'

const queryClient = new QueryClient({
    defaultOptions: {
        queries: {
            staleTime: 100_000,
        },
    },
});
ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <QueryClientProvider client={queryClient}>
            <App />
        </QueryClientProvider>

    </React.StrictMode>,
)