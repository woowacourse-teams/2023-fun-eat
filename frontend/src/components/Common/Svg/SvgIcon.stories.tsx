import { theme } from '@fun-eat/design-system';
import type { Meta, StoryObj } from '@storybook/react';

import SvgIcon, { SVG_ICON_VARIANTS } from './SvgIcon';

const meta: Meta<typeof SvgIcon> = {
  title: 'common/SvgIcon',
  component: SvgIcon,
  argTypes: {
    color: {
      control: {
        type: 'color',
      },
    },
  },
  args: {
    variant: 'recipe',
    color: theme.colors.gray4,
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

export const SvgIcons: Story = {
  render: () => {
    return (
      <>
        {Object.values(SVG_ICON_VARIANTS).map((variant) => (
          <SvgIcon key={variant} variant={variant} />
        ))}
      </>
    );
  },
};
