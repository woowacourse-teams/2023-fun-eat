import { Link } from '@fun-eat/design-system';
import styled from 'styled-components';

import { useBannerQuery } from '@/hooks/queries/banner';

const Banner = () => {
  const { data: banners } = useBannerQuery();
  const { link, image } = banners[Math.floor(Math.random() * banners.length)];

  if (!link) {
    return <BannerImage src={image} width={600} height={360} alt="배너" />;
  }

  return (
    <Link href={link} isExternal>
      <BannerImage src={image} width={600} height={360} alt="배너" />
    </Link>
  );
};

export default Banner;

const BannerImage = styled.img`
  width: 100%;
  height: auto;
`;
