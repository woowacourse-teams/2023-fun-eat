import { Text } from '@fun-eat/design-system';

import { DefaultLayout } from '@/components/Layout';

const NotFoundPage = () => {
  return (
    <DefaultLayout>
      <Text>요청하신 페이지를 찾을 수 없습니다.</Text>
    </DefaultLayout>
  );
};

export default NotFoundPage;
