package open.digytal.util.desktop;

import open.digytal.util.Imagem;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;

public class CampoImagem extends JPanel {
	private JLabel frame = new JLabel("");
	private File arquivo;
	
	public CampoImagem() {
		//imagemPadrao();
		BotaoIcone btRemover = new BotaoIcone();
		btRemover.setText("Limpar");

		BotaoIcone btSelecionar = new BotaoIcone();
		btSelecionar.setText("Abrir");
		btSelecionar.setIcone("pasta");

		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		GridBagConstraints gbc_lblFrame = new GridBagConstraints();
		gbc_lblFrame.insets = new Insets(3, 3, 10, 3);
		gbc_lblFrame.gridwidth = 2;
		gbc_lblFrame.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblFrame.gridx = 0;
		gbc_lblFrame.gridy = 0;
		add(frame, gbc_lblFrame);

		GridBagConstraints gbc_btSelecionar = new GridBagConstraints();
		gbc_btSelecionar.anchor = GridBagConstraints.NORTHWEST;
		gbc_btSelecionar.insets = new Insets(2, 10, 5, 0);
		gbc_btSelecionar.gridx = 0;
		gbc_btSelecionar.gridy = 1;
		add(btSelecionar, gbc_btSelecionar);

		btRemover.setIcone("remover");
		GridBagConstraints gbc_btRemover = new GridBagConstraints();
		gbc_btRemover.anchor = GridBagConstraints.NORTHWEST;
		gbc_btRemover.insets = new Insets(2, 10, 5, 0);
		gbc_btRemover.gridx = 1;
		gbc_btRemover.gridy = 1;
		add(btRemover, gbc_btRemover);

		btRemover.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				remover();
			}

		});
		btSelecionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selecionar();
			}

		});

	}

	private void imagemPadrao() {
		ImageIcon image = Imagem.png("semimagem");
		setImagem(image, 120);
	}

	public byte[] getBytes() {
		try {
			byte[] imageInByte;
			BufferedImage originalImage = ImageIO.read(arquivo);
			// convert BufferedImage to byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos);
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();

			return imageInByte;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void setImagem(byte[] bytes) {
		try {
			if (bytes != null) {
				try {
					InputStream in = new ByteArrayInputStream(bytes);
					BufferedImage bImageFromConvert = ImageIO.read(in);
					ImageIO.write(bImageFromConvert, "jpg", new File("c:/boxs/new-darksouls.jpg"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// setImagem(new ImageIcon(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setImagem(ImageIcon imagem) {
		setImagem(imagem, 100);
	}

	public void setImagem(ImageIcon imagem, int tamanho) {
		// this.imagem = imagem;
		imagem = new ImageIcon(imagem.getImage().getScaledInstance(tamanho, tamanho, Image.SCALE_DEFAULT));
		frame.setIcon(imagem);
		this.repaint();
	}

	private void selecionar() {
		JFileChooser file = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
		file.addChoosableFileFilter(filter);
		int result = file.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			arquivo = file.getSelectedFile();
			ImageIcon imagem = new ImageIcon(arquivo.getAbsolutePath());
			setImagem(imagem, 120);
		} else if (result == JFileChooser.CANCEL_OPTION) {
			imagemPadrao();
		}
	}

	private void remover() {
		imagemPadrao();
	}
}
