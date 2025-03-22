import React from 'react';
import {View, Text, TouchableOpacity} from 'react-native';
import {
  Selection,
  Decoration,
  DecorationType,
} from 'react-native-readium-nitro';

interface SelectionMenuProps {
  selection: Selection;
  onClose: () => void;
  onAddDecoration: (type: DecorationType) => void;
}

export const SelectionMenu: React.FC<SelectionMenuProps> = ({
  selection,
  onClose,
  onAddDecoration,
}) => {
  const {rect} = selection;
  console.log(rect);

  if (!rect) {
    return null;
  }

  return (
    <View
      style={{
        position: 'absolute',
        top: rect.top - 40,
        left: (rect.right + rect.left) / 2,
        backgroundColor: 'white',
        borderRadius: 4,
        padding: 8,
        flexDirection: 'row',
        shadowColor: '#000',
        shadowOffset: {width: 0, height: 2},
        shadowOpacity: 0.2,
        shadowRadius: 4,
        elevation: 4,
      }}>
      <TouchableOpacity
        style={{marginRight: 8}}
        onPress={() => {
          onAddDecoration('highlight');
          onClose();
        }}>
        <Text>Highlight</Text>
      </TouchableOpacity>
      <TouchableOpacity
        onPress={() => {
          onAddDecoration('underline');
          onClose();
        }}>
        <Text>Underline</Text>
      </TouchableOpacity>
    </View>
  );
};
