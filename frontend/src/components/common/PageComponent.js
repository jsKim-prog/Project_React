const PageComponent = ({ serverData, movePage, cursorStyle, setCursorStyle, searchQuery }) => {
  return (
    <div className="page-buttons">
      {/* Prev Button */}
      {serverData.prev && (
        <div
          onClick={() => movePage({ page: serverData.prevPage, searchQuery })}
          className="p-2 w-16 text-center font-bold text-blue-400 cursor-pointer"
          onMouseEnter={() => setCursorStyle('pointer')} // 마우스를 올리면 포인터로 변경
          onMouseLeave={() => setCursorStyle('default')} // 마우스를 벗어나면 기본 커서로 복원
          style={{ cursor: cursorStyle }} // 커서 스타일 적용
        >
          Prev
        </div>
      )}

      <div className={`p-2 w-12 text-center rounded text-blue cursor-pointer`}>
        {/* Page Number Buttons */}
        {serverData.pageNumList.map((pageNum) => (
          <i
            key={pageNum}
            className={`p-2 w-12 ms-3 ${serverData.current === pageNum ? 'ms-3' : 'ms-3'}`}
            onClick={() => movePage({ page: pageNum, searchQuery })}
            onMouseEnter={() => setCursorStyle('pointer')} // 마우스를 올리면 포인터로 변경
            onMouseLeave={() => setCursorStyle('default')} // 마우스를 벗어나면 기본 커서로 복원
            style={{ cursor: cursorStyle }} // 커서 스타일 적용
          >
            {pageNum}
          </i>
        ))}
      </div>

      {/* Next Button */}
      {serverData.next && (
        <div
          onClick={() => movePage({ page: serverData.nextPage, searchQuery })}
          className="p-2 w-16 text-center font-bold text-blue-400 cursor-pointer"
          onMouseEnter={() => setCursorStyle('pointer')} // 마우스를 올리면 포인터로 변경
          onMouseLeave={() => setCursorStyle('default')} // 마우스를 벗어나면 기본 커서로 복원
          style={{ cursor: cursorStyle }} // 커서 스타일 적용
        >
          Next
        </div>
      )}
    </div>
  );
};

export default PageComponent;
