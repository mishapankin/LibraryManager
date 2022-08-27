import Table from "./Table.jsx";
import {useEffect, useState} from "react";
import {useFetch} from "../hooks.js";
import Filter from  "../assets/filter.svg"
import SearchBox from "./SearchBox.jsx";

const headers = ["Название", "Автор", "Количество", "Осталось", "ISBN", "Издательство"];

const createData = (row) => [row.title, row.author.name, row.total, row.available, row.isbn, row.publisher.name];

const Books = () => {
    const [data, setData] = useState([]);
    const [authorName, setAuthorName] = useState("");
    const [title, setTitle] = useState("")

    useFetch("/api/get/books?" +
        new URLSearchParams({author: authorName, title: title}),
        {}, (dat) => setData(dat.map(createData)), [authorName, title]);

    return (
        <div>
            <div className="PageTitle">Книги</div>
            <div className="Filters">
                <img src={Filter} className="Icon"/>
                <SearchBox val={authorName} setVal={setAuthorName}/>
                <SearchBox val={title} setVal={setTitle}/>
            </div>
            <Table headers={headers} data={data}/>
        </div>
    );
};

export default Books;