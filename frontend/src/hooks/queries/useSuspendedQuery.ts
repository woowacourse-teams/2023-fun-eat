import { useQuery } from '@tanstack/react-query';
import type { QueryFunction, QueryKey, UseQueryOptions, UseQueryResult } from '@tanstack/react-query';

export type UseSuspendedQueryResult<TData> = Omit<
  UseQueryResult<TData, never>,
  'error' | 'isLoading' | 'isError' | 'isFetching' | 'status' | 'data'
> & { data: TData; status: 'idle' | 'success' };

export type UseSuspendedQueryResultOnSuccess<TData> = UseSuspendedQueryResult<TData> & {
  status: 'success';
  isSuccess: true;
  isIdle: false;
};

export type UseSuspendedQueryResultOnIdle = UseSuspendedQueryResult<undefined> & {
  status: 'idle';
  isSuccess: false;
  isIdle: true;
};

export type UseSuspendedQueryOptions<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
> = Omit<UseQueryOptions<TQueryFnData, TError, TData, TQueryKey>, 'suspense' | 'queryKey' | 'queryFn'>;

export type UseSuspendedQueryOptionWithoutEnabled<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
> = Omit<UseSuspendedQueryOptions<TQueryFnData, TError, TData, TQueryKey>, 'enabled'>;

export function useSuspendedQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options?: UseSuspendedQueryOptionWithoutEnabled<TQueryFnData, TError, TData, TQueryKey>
): UseSuspendedQueryResultOnSuccess<TData>;

export function useSuspendedQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options: UseSuspendedQueryOptionWithoutEnabled<TQueryFnData, TError, TData, TQueryKey> & { enabled?: true }
): UseSuspendedQueryResultOnSuccess<TData>;

export function useSuspendedQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options: UseSuspendedQueryOptionWithoutEnabled<TQueryFnData, TError, TData, TQueryKey> & { enabled: false }
): UseSuspendedQueryResultOnIdle;

export function useSuspendedQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options: UseSuspendedQueryOptionWithoutEnabled<TQueryFnData, TError, TData, TQueryKey>
): UseSuspendedQueryResultOnSuccess<TData> | UseSuspendedQueryResultOnIdle;

export function useSuspendedQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options?: UseSuspendedQueryOptions<TQueryFnData, TError, TData, TQueryKey>
) {
  return useQuery({
    queryKey,
    queryFn,
    suspense: true,
    ...options,
  }) as UseSuspendedQueryResult<TData>;
}
