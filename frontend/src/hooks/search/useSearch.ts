import type { ChangeEventHandler, FormEventHandler, MouseEventHandler } from 'react';
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

  const handleSearch: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();
    const trimmedSearchQuery = searchQuery.trim();

    if (!trimmedSearchQuery) {
      alert('검색어를 입력해주세요');
      setSearchQuery('');
      return;
    }

    if (currentSearchQuery === trimmedSearchQuery) {
      return;
    }

    setSearchQuery(trimmedSearchQuery);
    setIsSubmitted(true);
    setSearchParams({ query: trimmedSearchQuery });
  };

  const handleSearchClick: MouseEventHandler<HTMLButtonElement> = (event) => {
    const { value } = event.currentTarget;

    setSearchQuery(value);
    setIsSubmitted(true);
    setSearchParams({ query: value });
  };

  return { searchQuery, isSubmitted, handleSearchQuery, handleSearch, handleSearchClick };
};

export default useSearch;
