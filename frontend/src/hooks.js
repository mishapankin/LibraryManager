import {useEffect} from "react";

const useFetch = (url, args, onload, deps) => {
    useEffect(() => {
            fetch(url, args)
                .then(data => data.json())
                .then(data => onload(data))
        },
        deps
    )
}

const postBodyDefault = {
    method: "POST",
    headers: {'Content-Type': 'application/json'}
};

const postRequest = (url, body) =>
    fetch(url, {...postBodyDefault, body: JSON.stringify(body)})
        .then(res => res.json());

export { useFetch, postRequest };