import React, {useEffect, useState} from 'react';
import './DataView.css'

function DataView() {
    const [data, setData] = useState([])
    const [selectedRow, setSelectedRow] = useState(null);

    const [name, setName] = useState('');
    const [groupAge, setGroupAge] = useState('');

    // HTTP request
    useEffect(() => {
        fetch('http://localhost:8080/contest/challenge')
            .then(response => response.json())
            .then(data => setData(data))
            .catch(error => console.error('Error fetching data: ', error));
    }, []);

    const handleRowClick = (item) => {
        setSelectedRow(item);
        setName(item.name);
        setGroupAge(item.groupAge);
    };

    const handleAdd = () => {
        const newRow = {name, groupAge, numberOfParticipants: 0};
        fetch(`http://localhost:8080/contest/challenge`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newRow)
        })
            .then(response => response.json())
            .then(data => setData(prevData => [...prevData, data]))
            .catch(error => console.error('Error adding data:', error));
    };

    const handleUpdate = () => {
        let id = selectedRow.id;
        let numberOfParticipants = selectedRow.numberOfParticipants;
        const updatedRow = {id, name, groupAge, numberOfParticipants};
        fetch(`http://localhost:8080/contest/challenge/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedRow)
        })
            .then(response => response.json())
            .then(data => setData(prevData => prevData.map(item => item.id === id ? data : item)))
            .catch(error => console.error('Error updating data:', error));
    };

    const handleDelete = () => {
        let id = selectedRow.id;
        fetch(`http://localhost:8080/contest/challenge/${id}`, {
            method: 'DELETE'
        })
            .then(response => setData(prevData => prevData.filter(item => item.id !== id)))
            .catch(error => console.error('Error deleting data:', error));
    };

    return (
        <div className="DataView">
            <div className="CrudOperation">
                <div>
                    <form>
                        <label>
                            Name
                            <input type="text"
                                   value={name}
                                   onChange={(e) => setName(e.target.value)}
                            />
                        </label>
                        <label>
                            Group age
                            <input type="text"
                                   value={groupAge}
                                   onChange={(e) => setGroupAge(e.target.value)}/>
                        </label>
                    </form>
                </div>
                <div className="Operations">
                    <button onClick={handleAdd}>Add</button>
                    <button onClick={handleUpdate}
                            disabled={!selectedRow}
                    >Update
                    </button>
                    <button onClick={handleDelete}
                            disabled={!selectedRow}
                    >Delete
                    </button>
                </div>
            </div>
            <table className="table">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Group age</th>
                    <th>No. participants</th>
                </tr>
                </thead>
                <tbody>
                {data.map((item) => (
                    <tr key={item.id}
                        onClick={() => handleRowClick(item)}
                        className={selectedRow === item ? 'selected-row' : ''}>
                        <td>{item.id}</td>
                        <td>{item.name}</td>
                        <td>{item.groupAge}</td>
                        <td>{item.numberOfParticipants}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default DataView;