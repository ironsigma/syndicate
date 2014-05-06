<?php
namespace Cyniris\SyndicateBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="subscription")
 */
class Subscription {
    /**
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     * @ORM\Column(type="integer")
     */
    protected $id;

    /**
     * @ORM\ManyToOne(targetEntity="Feed")
     */
    protected $feed;

    /**
     * @ORM\ManyToOne(targetEntity="Folder")
     */
    protected $folder;

    /**
     * Get id
     *
     * @return integer 
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set feed
     *
     * @param \Cyniris\SyndicateBundle\Entity\Feed $feed
     * @return Subscription
     */
    public function setFeed(\Cyniris\SyndicateBundle\Entity\Feed $feed = null)
    {
        $this->feed = $feed;

        return $this;
    }

    /**
     * Get feed
     *
     * @return \Cyniris\SyndicateBundle\Entity\Feed 
     */
    public function getFeed()
    {
        return $this->feed;
    }

    /**
     * Set folder
     *
     * @param \Cyniris\SyndicateBundle\Entity\Folder $folder
     * @return Subscription
     */
    public function setFolder(\Cyniris\SyndicateBundle\Entity\Folder $folder = null)
    {
        $this->folder = $folder;

        return $this;
    }

    /**
     * Get folder
     *
     * @return \Cyniris\SyndicateBundle\Entity\Folder 
     */
    public function getFolder()
    {
        return $this->folder;
    }
}
