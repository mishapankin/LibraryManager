import Table from "./Table.jsx";
import {useEffect, useState} from "react";
import {useFetch} from "../hooks.js";
import Filter from  "../assets/filter.svg"

const headers = ["Название", "Автор", "Количество", "Осталось", "ISBN", "Издательство"];

const createData = (row) => [row.title, row.author.name, row.total, row.available, row.isbn, row.publisher.name];

const Books = () => {
    const [data, setData] = useState([]);
    const [authorName, setAuthorName] = useState("");

    useFetch("/api/get/books?" +
        new URLSearchParams({author: authorName, title: ""}),
        {}, (dat) => setData(dat.map(createData)), [authorName]);

    return (
        <div>
            <div className="PageTitle">Книги</div>
            <div className="Filters">
                <img src={Filter} className="Icon"/>
                <input onChange={(e) => setAuthorName(e.target.value)} value={authorName}/>
            </div>
            <Table headers={headers} data={data}/>
        </div>
    );
};

export default Books;