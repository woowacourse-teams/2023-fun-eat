import type { ChangeEventHandler, FormEventHandler, MouseEventHandler } from 'react';
import { useRef, useState } from 'react';
import { useSearchParams } from 'react-router-dom';

import { useGA } from '../common';
import { useToastActionContext } from '../context';

const useSearch = () => {
  const inputRef = useRef<HTMLInputElement>(null);

  const [searchParams, setSearchParams] = useSearchParams();
  const currentSearchQuery = searchParams.get('query');

  const [searchQuery, setSearchQuery] = useState(currentSearchQuery || '');
  const [isSubmitted, setIsSubmitted] = useState(!!currentSearchQuery);
  const [isAutocompleteOpen, setIsAutocompleteOpen] = useState(searchQuery.length > 0);

  const { toast } = useToastActionContext();

  const { gaEvent } = useGA();

  const focusInput = () => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  };

  const handleSearchQuery: ChangeEventHandler<HTMLInputElement> = (event) => {
    setIsSubmitted(false);
    setSearchQuery(event.currentTarget.value);
    setIsAutocompleteOpen(event.currentTarget.value.length > 0);
  };

  const handleSearch: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();
    gaEvent({ category: 'submit', action: '검색 페이지에서 검색', label: '검색' });

    const trimmedSearchQuery = searchQuery.trim();

    if (!trimmedSearchQuery) {
      toast.error('검색어를 입력해주세요');
      focusInput();
      resetSearchQuery();
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

  const handleAutocompleteClose = () => {
    setIsAutocompleteOpen(false);
  };

  const resetSearchQuery = () => {
    setSearchQuery('');
  };

  return {
    inputRef,
    searchQuery,
    isSubmitted,
    isAutocompleteOpen,
    handleSearchQuery,
    handleSearch,
    handleSearchClick,
    handleAutocompleteClose,
    resetSearchQuery,
  };
};

export default useSearch;
