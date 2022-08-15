import Table from "./Table.jsx";

const data = [[1, 2, 3, 4]];

const headers = ["Читатель", "Тип", "Книга", "Дата"];

const Operations = () => {
    return (
        <div>
            <div className="PageTitle">Операции</div>
            <Table headers={headers} data={data}/>
        </div>
    );
};

export default Operations;