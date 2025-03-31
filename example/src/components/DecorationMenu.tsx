import React from 'react';
import {View, Text, TouchableOpacity} from 'react-native';
import {
  Decoration,
  DecorationType,
  ActivatedDecoration,
} from 'react-native-nitro-readium';

interface DecorationMenuProps {
  activatedDecoration: ActivatedDecoration;
  onClose: () => void;
  onRemoveDecoration: (id: string) => void;
}

export const DecorationMenu: React.FC<DecorationMenuProps> = ({
  activatedDecoration,
  onClose,
  onRemoveDecoration,
}) => {
  const {rect} = activatedDecoration;

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
        onPress={() => {
          onRemoveDecoration(activatedDecoration.decoration.id);
          onClose();
        }}>
        <Text style={{color: 'red'}}>Remove</Text>
      </TouchableOpacity>
    </View>
  );
};
