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

export { useFetch };