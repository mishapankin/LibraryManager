import {useEffect} from "react";

const useFetch = (url, getArgs, args, onload, deps) => {
    useEffect(() => {
            fetch(url + new URLSearchParams(getArgs), args)
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

const postRequest = (url, body, onload) => {
    (async () => {
        let res = await fetch(url, {...postBodyDefault, body: JSON.stringify(body)});
        let parsed = await res.json();
        onload(parsed, res.status);
    })();
}

export { useFetch, postRequest };