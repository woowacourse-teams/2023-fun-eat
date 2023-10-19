import { useSuspendedQuery } from '../useSuspendedQuery';

import { bannerApi } from '@/apis';
import type { Banner } from '@/types/banner';

const fetchBanner = async () => {
  const response = await bannerApi.get({});
  const data: Banner[] = await response.json();
  return data;
};

const useBannerQuery = () => {
  return useSuspendedQuery(['banner'], () => fetchBanner());
};

export default useBannerQuery;
