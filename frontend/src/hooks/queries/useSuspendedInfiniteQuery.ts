import {
  type InfiniteData,
  type QueryKey,
  type UseInfiniteQueryResult,
  type UseInfiniteQueryOptions,
  type QueryFunction,
  useInfiniteQuery,
} from '@tanstack/react-query';

export type UseSuspendedInfiniteQueryResult<TData = undefined> = Omit<
  UseInfiniteQueryResult<TData, never>,
  'error' | 'isLoading' | 'isError' | 'isFetching' | 'status' | 'data'
>;

export type UseSuspendedInfiniteQueryResultOnSuccess<TData> = UseSuspendedInfiniteQueryResult<TData> & {
  data: InfiniteData<TData>;
  status: 'success';
  isSuccess: true;
  isIdle: false;
};

export type UseSuspendedInfiniteQueryResultOnIdle = UseSuspendedInfiniteQueryResult & {
  data: undefined;
  status: 'idle';
  isSuccess: false;
  isIdle: true;
};

export type UseSuspendedInfiniteQueryOptions<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
> = Omit<
  UseInfiniteQueryOptions<TQueryFnData, TError, TData, TQueryData, TQueryKey>,
  'suspense' | 'queryKey' | 'queryFn'
>;

export type UseSuspendedInfiniteQueryOptionWithoutEnabled<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
> = Omit<UseSuspendedInfiniteQueryOptions<TQueryFnData, TError, TData, TQueryData, TQueryKey>, 'enabled'>;

export function useSuspendedInfiniteQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options?: UseSuspendedInfiniteQueryOptionWithoutEnabled<TQueryFnData, TError, TData, TQueryFnData, TQueryKey>
): UseSuspendedInfiniteQueryResultOnSuccess<TData>;

export function useSuspendedInfiniteQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options?: UseSuspendedInfiniteQueryOptionWithoutEnabled<TQueryFnData, TError, TData, TQueryFnData, TQueryKey> & {
    enabled?: true;
  }
): UseSuspendedInfiniteQueryResultOnSuccess<TData>;

export function useSuspendedInfiniteQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options: UseSuspendedInfiniteQueryOptionWithoutEnabled<TQueryFnData, TError, TData, TQueryFnData, TQueryKey> & {
    enabled: false;
  }
): UseSuspendedInfiniteQueryResultOnIdle;

export function useSuspendedInfiniteQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options?: UseSuspendedInfiniteQueryOptions<TQueryFnData, TError, TData, TQueryFnData, TQueryKey>
) {
  return useInfiniteQuery({
    queryKey,
    queryFn,
    suspense: true,
    ...options,
  }) as UseSuspendedInfiniteQueryResult<TData>;
}

export default useSuspendedInfiniteQuery;
