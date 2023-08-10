import type { ChangeEventHandler, FormEventHandler } from 'react';
import { useState } from 'react';
import { useSearchParams } from 'react-router-dom';

const useSearch = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const currentSearchQuery = searchParams.get('query');

  const [searchQuery, setSearchQuery] = useState(currentSearchQuery || '');
  const [isSubmitted, setIsSubmitted] = useState(!!currentSearchQuery);

  const handleSearchQuery: ChangeEventHandler<HTMLInputElement> = (event) => {
    setIsSubmitted(false);
    setSearchQuery(event.currentTarget.value);
  };

  const handleSubmit: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();

    if (!searchQuery) {
      alert('검색어를 입력해주세요');
      return;
    }

    if (currentSearchQuery === searchQuery) {
      return;
    }

    setIsSubmitted(true);
    setSearchParams({ query: searchQuery });
  };

  return { searchQuery, isSubmitted, handleSearchQuery, handleSubmit };
};

export default useSearch;
