const SearchBox = ({ val, setVal }) => {
    return <input onChange={(e) => setVal(e.target.value)} value={val}/>;
};

export default SearchBox;