		/*for (int i=0;i<3;i++){//rows
			for (int j=0;j<7;j++){
				ImageIcon pic = new ImageIcon("Real Pok Pics\\"+pnames[count]+".gif");
				Image img = pic.getImage();
				img = img.getScaledInstance(60,60,Image.SCALE_SMOOTH);
				pokButtons[count] = new JButton(new ImageIcon(img));
				pokButtons[count].addActionListener(this);
				pokButtons[count].setSize(60,60);
				pokButtons[count].setLocation(30+j*(img.getWidth(null)+30),200-30+i*(img.getHeight(null)+20));
				add(pokButtons[count]);
				count++;
			}
		}
		for (int i=0;i<6;i++){
			ImageIcon pic = new ImageIcon("Real Pok Pics\\"+pnames[count]+".gif");
			Image img = pic.getImage();
			img = img.getScaledInstance(60,60,Image.SCALE_SMOOTH);
			pokButtons[count] = new JButton(new ImageIcon(img));
			pokButtons[count].addActionListener(this);
			pokButtons[count].setSize(60,60);
			pokButtons[count].setLocation(30+40+i*(img.getWidth(null)+30),200-30+3*(img.getHeight(null)+20));
			add(pokButtons[count]);
			count++;
		}
		done = new JButton("CONTINUE");
		done.addActionListener(this);
		done.setSize(100,50);
		done.setLocation(650,500);
		add(done);//done.setEnabled(true);
		done.setEnabled(false);*/
		
		
	    		/*
	    		//shuffle songs
	    		boolean[] temp = new boolean[played.length];
	    		Arrays.fill(temp,true);
	    		
	    		if (Arrays.equals(temp,played)){
	    			Arrays.fill(played,false);
	    		}
	    		
	    		while (true){
	    			int rand = (int)(Math.random()*(music.length));
	    			//System.out.println(rand);
	    			if (played[rand]==false){
	    				soundFile = music[rand];
	    				played[rand] = true;
	    				break;
	    			}
	    		}
	    	}*/